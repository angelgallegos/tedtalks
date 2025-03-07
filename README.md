# iO Knowledge Sharing Platform: TedTalks Edition

Assignment by Angel Gallegos Andrade

## Requirements

 - Java >= 17
 - MySQL > 8

## Configuration

The project uses MySQL to store the Speakers and Talks. As such we need to connect to a DB, update the next values on the
appropriate .properties file(dev, local, prod):
```properties
spring.datasource.url=
spring.datasource.username=
spring.datasource.password=
```
An application-dev.properties is provided as an example. 

#### Note: 
Don't forget to set the spring.profiles.active environment variable to load the corresponding properties file 

## Running the project
The easiest way
```shell
  ./gradlew build && java -jar build/libs/tedtalks-0.0.1-SNAPSHOT.jar
```

Alternatively there is a docker compose file so if you are using docker just run: 
```shell
    docker compose up --build
```

## Import the data from csv file

To import the data in the csv file:

```
    curl --request GET \
  --url '{baseUrl}/job/run/importTalksJob?fileName={fileName}'
```

The endpoint receives the name on the file from which the data should be imported.

For this MvP the file should be located in the project classpath, but on a real project the file should be retrieved from a different source, ideally a s3 bucket or similar. 

## Influence Analysis

Once the data is loaded we can list the speakers based on their influence(rating).
There are 2 different formulas used to order and determine a speaker's rating:

### Average
Very rudimentary formula using the relation of likes and views. avg = likes/views

### Wilson 
Lower bound of Wilson score confidence interval for a Bernoulli parameter([Article about it](https://en.wikipedia.org/wiki/Binomial_proportion_confidence_interval))

Represented by the next formula:

((positive + 1.9208) / (positive + negative) -
1.96 * SQRT((positive * negative) / (positive + negative) + 0.9604) /
(positive + negative)) / (1 + 3.8416 / (positive + negative))

It's of note that we don't actually have a negative input for the speakers based on the talks
so we use the total number of views minus the likes. 
 
Both ordered ratings can be listed through the next endpoint:  

```shell
  curl --request GET \
  --url '{baseUrl}/speaker/list/order-by-rating/{type}?page=1&size=10'
```

the path variable `type` determines which one will be used, and it can take either of these values: 
`average`, `wilson`.

## Data Management

There are a list of CRUD endpoints that allows the management of both talks and speakers data
Following is a summarized list of curls for said endpoints:

### Speakers

#### Create
```shell
  curl --request POST \
  --url http://localhost:8080/speaker \
  --header 'Content-Type: application/json' \
  --data '{
	"name": ""
    }'
```

#### Retrieve
```shell
  curl --request GET \
  --url {baseUrl}/speaker/{id}
```

#### Update
```shell
  curl --request PUT \
  --url http://localhost:8080/speaker/{id} \
  --header 'Content-Type: application/json' \
  --data '{
	"name": ""
}'
```
#### Delete
Delete hasn't been implemented for the speaker entity

### Talks

#### Create
```shell
   curl --request POST \
  --url {baseUrl}/talk \
  --header 'Content-Type: application/json' \
  --data '{
	"title":"",
	"link": "",
	"date": "",
	"views": 0,
	"likes": 0,
	"speaker": {
		"id": ""
	}
}'
```
Date is accepted in format: yyyy-MM-dd HH:mm

#### Retrieve
```shell
  curl --request GET \
  --url {baseUrl}/talk/{url}
```

#### Update
```shell
   curl --request PUT \
  --url {baseUrl}/talk/{id} \
  --header 'Content-Type: application/json' \
  --data '{
	"title":"",
	"link": "",
	"date": "",
	"views": 0,
	"likes": 0,
  }'
```

#### Delete
```shell
   curl --request DELETE \
  --url {baseUrl}/talk/{id} 
```