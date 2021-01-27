#!/usr/bin/env bash
backend=$2
server_name=$3
haproxy_sock_path="/usr/local/haproxy/haproxy.sock"
stop(){
    # 返回 0 失败  返回 1 已经停了
    echo "检测$backend/$server_name"
    title_array=(`echo "show servers state" | socat stdio $haproxy_sock_path|head -n 2|tail -n 1|sed 's/# //g'`) # 获取第二行数据 抬头行
    srv_op_state_index=-1 #定义对应下标
    for (( i = 0; i < ${#title_array[@]}; ++ i )); do
        #echo ${title_array[i]}
        if [ ${title_array[i]} = "srv_op_state" ]; then
            srv_op_state_index=$i;
            break
        fi
    done
    if [ ${srv_op_state_index} = -1 ]; then
        echo "下标 $srv_op_state_index,检查服务器是否安装haproxy 或者 socat软件"
        return 0
    fi
    echo "执行 echo "show servers state" | socat stdio $haproxy_sock_path|grep .*$backend.*$server_name"
    data_array=(`echo "show servers state" | socat stdio $haproxy_sock_path|grep .*$backend.*$server_name`) # 获取第二行数据 抬头行\
    if [ ${#data_array[@]} = 0 ]; then
        echo "服务 $backend/$server_name 不存在，请检查服务名！"
        return 0
    fi
    echo "值 ${data_array[srv_op_state_index]}"
    if [[ ${data_array[srv_op_state_index]} = "0" ]]; then
        echo "服务已经停止。。。"
        return 1
    else
        eval `echo "disable server $backend/$server_name" | socat stdio $haproxy_sock_path`
    fi
    while [[ ${data_array[srv_op_state_index]} != "0" ]]; do
        data_array=(`echo "show servers state" | socat stdio $haproxy_sock_path|grep .*$backend.*$server_name`) # 获取第二行数据 抬头行
        echo ${data_array[srv_op_state_index]}
        sleep 1
    done
    echo "停止成功~~"
    return 1
}

start(){
    # 返回 -1 失败  返回 1 已经停了   返回 2 成功了
    echo "检测$backend/$server_name"
    title_array=(`echo "show servers state" | socat stdio $haproxy_sock_path|head -n 2|tail -n 1|sed 's/# //g'`) # 获取第二行数据 抬头行
    srv_op_state_index=-1 #定义对应下标
    for (( i = 0; i < ${#title_array[@]}; ++ i )); do
        if [[ ${title_array[i]} = "srv_op_state" ]]; then
            srv_op_state_index=$i;
            break
        fi
    done
    echo "下标 $srv_op_state_index"
    if [ ${srv_op_state_index} = -1 ]; then
        echo "下标 $srv_op_state_index,检查服务器是否安装haproxy 或者 socat软件"
        return 0
    fi
    echo "执行 echo "show servers state" | socat stdio $haproxy_sock_path|grep .*$backend.*$server_name"
    data_array=(`echo "show servers state" | socat stdio $haproxy_sock_path|grep .*$backend.*$server_name`) # 获取第二行数据 抬头行
    if [ ${#data_array[@]} = 0 ]; then
        echo "服务 *$backend/$server_name 不存在，请检查服务名！"
        return 0
    fi
    echo "值 ${data_array[srv_op_state_index]}"
    if [[ ${data_array[$srv_op_state_index]} = "2" ]]; then
        echo "服务已经启动。。。"
        return 1
    else
        eval `echo "enable server $backend/$server_name" | socat stdio $haproxy_sock_path`
    fi
    while [[ ${data_array[$srv_op_state_index]} != "2" ]]; do
        sleep 1
        data_array=(`echo "show servers state" | socat stdio $haproxy_sock_path|grep .*$backend.*$server_name`) # 获取第二行数据 抬头行
        break;
    done
    echo "启动成功~~"
    return 1;
}



restart(){
    stop_state=0;
    start_state=0;
    echo "停止服务"
    stop
    stop_state=`echo $?`
    if [[ ${stop_state} = 0 ]]; then
        echo "停止服务失败"
        return 0
    fi
    echo "停止成功"
    echo "重启服务"
    start
    start_state=`echo $?`
    if [[ ${start_state} = 0 ]]; then
        echo "启动服务失败"
        return 0
    fi
    echo "重启成功"
    return 1;
}


case $1 in
    start) start;;
    stop)  stop;;
    restart)  restart;;
    status)  status;;
    *)  echo "require start|stop|restart|status"  ;;
esac

