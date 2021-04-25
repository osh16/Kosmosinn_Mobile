# **Kosmosinn 2: Electric Boogaloo**

**Spjallborð alþýðunnar**

Kosmósinn is a bulletin board website

We require a large and vibrant userbase which views this website as its default

We can monetize the website through:
* frivolous cosmetics for donators (e.g. avatars, colored name, etc)
* users can award others for their contributions (e.g. reddit gold)
* general e-begging (we say its for server costs)

# Server/Website
To run the server for the app

	$ cd Server
	$ ./mvnw spring-boot:run

In development process, to compile:
	
	$ mvn compile

Add a jwt secret key with:
	
	$ echo "jwt.token.secret=\"$(openssl rand -base64 512 | tr -d "\n\r")\"" >> Server/src/main/resources/application.properties

Also add expiration (in miliseconds):

	$ echo "jwt.token.expiration=180000" >> Server/src/main/resources/application.properties
