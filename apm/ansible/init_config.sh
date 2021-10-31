#!/bin/sh 

mercury_node_list=`sed -n '/^\[mercury_node/,/^\[prometheus_server/p' hosts | grep -v "^\["`

set_exporter() {
    node_exporters=""
    mercury_exporters=""
    promtail_agents=""
    pgsql_exporters=""
    for i in ${mercury_node_list};
      do
        node_exporter=\"${i}:9100\"
        promtail_agent=\"${i}:9080\"
        mercury_exporter=\"${i}:8116\"
        pgsql_exporter=\"${i}:8187\"
       

        node_exporters=${node_exporters},${node_exporter}
        promtail_agents=${promtail_agents},${promtail_agent}
        mercury_exporters=${mercury_exporters},${mercury_exporter}
        pgsql_exporters=${pgsql_exporters},${pgsql_exporter}

    done
     

    node_exporters=`echo ${node_exporters} | sed 's/^.//1'`
    mercury_exporters=`echo ${mercury_exporters} | sed 's/^.//1'`
    promtail_agents=`echo ${promtail_agents} | sed 's/^.//1'`
    pgsql_exporters=`echo ${pgsql_exporters} | sed 's/^.//1'`
    echo "${node_exporters}"
    echo "${mercury_exporters}"
    echo "${promtail_agents}"
    echo "${pgsql_exporters}"

    
    # cp -rp ./roles/prometheus/templates/prometheus.yml.j2 ./roles/prometheus/templates/prometheus.yml_new.j2
    sed -i "s/node_exporter_ip:9100/${node_exporters}/g" "$1/config/promethues/prometheus.yml"
    sed -i "s/mercury_exporter_ip:8116/${mercury_exporters}/g" "$1/config/promethues/prometheus.yml"
    sed -i "s/promtail_agent_ip:9080/${promtail_agents}/g" "$1/config/promethues/prometheus.yml"
    sed -i "s/postgres_exporter_address/${pgsql_exporters}/g" "$1/config/promethues/prometheus.yml"
}

set_exporter $1
