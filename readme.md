# PhoneApp


## INSTALATION

### Considerations:
   - The app has been developed using JAVA (spring-boot as main framework), mongo as repository, docker (and docker-compose).
   - Build sources is include in docker process so build docker images maybe be slow.
   - Sometimes docker build maybe fail (due to network issues), if the error persist, please contact with me.
   - There are three artefactories and have REST comunication beetwen theirs.


### Required:
   - git client
   - docker-compose (https://docs.docker.com/compose/install/)
   - mongo client (https://docs.mongodb.com/manual/installation/)
   - The app claims for the next ports: 27017,8081,8082,8083

### Instructtions
   - download source: git clone https://github.com/jovenjoao/phoneApp.git
   - build sources: docker-compose build
   - Run up app: docker-compose up
   - Load initial data:
	 1. conect to mongodb: mongo localhost:27017/phone
	 2. run sentences:

		db.phone.save ({_id:"ref_1",images:["hola.jpg"],name:"Motorola 12", description:"mola mucho" , price : 120.5})
		db.phone.save ({_id:"ref_2",images:["hola2.jpg"],name:"Mi A1", description:"mola muchísimo" , price : 520.5})
		db.phone.save ({_id:"ref_3",images:["hola3.jpg"],name:"Iphone 6", description:"mola muchísimo max" , price : 800.12})


###EXECUTE
	
	curl localhost:8081/phone -> get all phones
	curl localhost:8081/phone/ref_3 -> get a phone

	curl localhost:8082/order/check  --data '{"email":"a@a.es","name":"a","surname":"b","phones":["ref_1","ref_2","ref_3"]}' --header "Content-Type: application/json"

	In tests only "ref_1","ref_2","ref_3" are valid other phone id is not in DB.

