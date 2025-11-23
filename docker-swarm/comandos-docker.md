# Comandos
> docker swarm init --advertise-addr <ip-aqui>
> docker node list
> docker node promote <node-name>
> docker stack deploy -c docker-compose.yml <cluster-name>
> docker service update --replicas=5 financeiro_gestaoacesso
> docker node update --availability drain <id>
> docker node update --availability active <id>
> docker node rm <id>

# Util
> export $(cat .env | xargs)
> git clone https://github.com/caiohclins/apoo-financeiro.git