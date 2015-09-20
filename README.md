# **go shorty**

[![docker](https://img.shields.io/badge/Docker-1.7.1-blue.svg?style=flat)](https://www.docker.com/)  [![AngularJS](https://img.shields.io/badge/AngularJS-1.3.0-red.svg?style=flat)](https://angular.io/) [![spark](https://img.shields.io/badge/Spark%20Java-2.2-orange.svg?style=flat)](http://sparkjava.com/) [![redis](https://img.shields.io/badge/Redis-3.0.4-red.svg?style=flat)](http://redis.io/) 

**URL shortener** service composed of three layers:

 - **Client**: Single Page Application made using **[AngularJS](https://angular.io/)**

 - **Server**: Server Side Application exposed over http made using **[Spark Java](http://sparkjava.com/)**
 - **NoSQL Database**: **[Redis](http://redis.io/)**

Everything runs inside **[Docker](https://www.docker.com/)** containers.



----------



## Installation

### Linux:
1. Install [docker](https://docs.docker.com/), [docker-compose](https://docs.docker.com/compose/install/) and [git](https://git-scm.com/book/it/v1/Per-Iniziare-Installare-Git)

2. Clone this repo:<pre>git clone https://github.com/GruppoPBDMNG-4/go-shorty.git </pre> <pre>cd go-shorty/</pre>

3. Run:<pre>docker-compose -p gruppo4 up -d</pre>


### Windows
1. Start [boot2docker](http://boot2docker.io/) or a [Docker Host](https://docs.docker.com/machine/install-machine/) 

	<pre>boot2docker start</pre> <pre>boot2docker ssh</pre>
2. Clone this repo <pre>git clone https://github.com/GruppoPBDMNG-4/go-shorty.git</pre> <pre>cd go-shorty/</pre>

3. Build the image <pre>docker build -t gruppo4/goshorty .</pre>

4. Run the containers <pre>docker run --name gruppo4-dataonly chrispiemo/data-only</pre> <pre>docker run -d --name gruppo4-redis --volumes-from gruppo4-dataonly redis:3.0.4 redis-server --appendonly yes</pre> <pre>docker run -d --name gruppo4-spark -p 4567:4567 --link gruppo4-redis:db gruppo4/goshorty</pre>

5. VirtualBox port forwarding 

	| Name | Protocol | Host IP | Host Port | Guest IP | Guest Port
	| :--: | :------: | :-----: | :-------: | :------: | :--------: 
	| goshorty | TCP  | 127.0.0.1 | 4567    | 0.0.0.0  | 4567 


----------


## Utilization
Open a web browser and go to <pre>http://localhost:4567</pre>

