# Introduction
mercury-devops 是用于配合 mercury 开发和运维的一套工具包，项目主要分为以下几个部分
- APM
- mercury-deploy
- mercury-benchmark
- mercury-test

# APM
主要分为三个模块:
- 监控面板
    - mercury-benchmark
    - PostgreSQL Database
    - mercury-node
- 日志查询

## 部署信息
url：http://127.0.0.0:8600
username：admin
password：admin


## 配置文件
### step1
```shell
$ cd {mercury-devops}/apm/ansible && vim mercury_apm_config.yml
```

### step2
```yml
# monitor
# monitor 部署目录
monitor_dir: /Users/zjh/Desktop/mercury-monitor
postgres_exporter_address: xxx.xxx.xxx.xxx:8187

# agent
# agent 部署目录
monitor_agent_dir: /Users/zjh/Desktop/agent
# mercury log 目录
log_path: \/Users\/zjh\/Documents\/cryptape\/mercury\/free-space\/testnet
# loki rpc 地址
loki_address: xxx.xxx.xxx.xxx



# pgsql-agent
# pgsql-agent 部署目录
monitor_pgsql_agent_dir: /Users/zjh/Desktop/pgsql-agent
# pgsql log 目录
pg_log_path: \/Users\/zjh\/Desktop\/test
# pgsql 连接
url: postgresql:\/\/postgres:123456@xxx.xxx.xxx.xxx:8432\/mercury?sslmode=disable
```

## 部署
### monitor
```shell
$ cd {mercury-devops}/apm/ansible && make monitor
```

### agent
```shell
$ cd {mercury-devops}/apm/ansible && make agent
```


### pgsql-agent
```shell
$ cd {mercury-devops}/apm/ansible && make pgsql
```

# mercury-deploy
通过 docker-compose 部署 mercury，包含以下组件:
- ckb-node
- mercury
- pgsql
- ckb-indexer (暂未集成)


## 配置文件
### step1
```shell
$ cd {mercury-devops}/mercury-deploy && vim config.yml
```

### step2
```yml

# mercury 部署目录
deploy_path: "/home/ckb/mercury-test"
# git repo
mercury_repo: "https://github.com/nervosnetwork/mercury.git"
# git branch
mercury_version: "main"
# ckb and mercury network
net_type: "testnet"
# 用于部署时 kill 老的进程，mainnet(修改为 老的进程，mainnet_config ) 和 testnet(修改为 testnet_config )
# mainnet: "devtools/config/mainnet_config.toml"
# testnet: "devtools/config/testnet_config.toml"
kill_name: "devtools/config/testnet_config.toml"
# 数据库密码配置
db_password: "123456"

```

## 部署
### monitor
```shell
$ cd {mercury-devops}/mercury-deploy && make deploy
```


# mercury-benchmark
使用 wrk 进行压测

## 配置文件
### step1
```shell
$ cd {mercury-devops}/mercury-benchmark && vim config.json
```

### step2
```yml

{
    "mainnet": {
        "node_url": "http://127.0.0.1:8116",
        "seconds": [60, 300, 900],
        "collections": [300, 1000],
        "threads": 16,
        "timeout": 1,
        "script_dir": "script/mainnet"
    },
    "testnet": {
        "node_url": "http://127.0.0.1:8116",
        "seconds": [60, 300, 900],
        "collections": [300, 1000],
        "threads": 16,
        "timeout": 1,
        "script_dir": "script/testnet"
    }
}

```
mainnet：主网配置
testnet：测试网配置
node_url：mercury address
seconds：wrk 压测时间，按秒计算
collections：wrk 连接数
threads：wrk 使用线程数
timeout： wrk 连接的超时时间
script_dir：压测脚本目录，不需要动

## 压测
### 主网压测
```shell
$ cd {mercury-devops}/mercury-benchmark && screen python3 main.py mainnet
```

### 测试网压测
```shell
$ cd {mercury-devops}/mercury-benchmark && screen python3 main.py testnet
```

### 压测指定脚本
```shell
$ cd {mercury-devops}/mercury-benchmark && screen python3 main.py testnet get_spent_transaction_double_entry.lua
```


# mercury-test
重构集成中，敬请期待！
