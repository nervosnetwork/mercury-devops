monitor:
	ansible-playbook -i hosts deploy_monitor.yml -e "@./mercury_apm_config.yml"

agent:
	ansible-playbook -i hosts deploy_monitor_agent.yml -e "@./mercury_apm_config.yml"

pgsql:
	ansible-playbook -i hosts deploy_monitor_pgsql_agent.yml -e "@./mercury_apm_config.yml"