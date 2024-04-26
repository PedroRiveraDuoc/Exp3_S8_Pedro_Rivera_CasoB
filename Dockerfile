#FROM openjdk:21-ea-24-oracle 
FROM openjdk:21-jdk
WORKDIR /app
#aqui debemos asegurarnos del nombre de nuestro jar coincida
COPY target/casob-0.0.1-SNAPSHOT.jar app.jar

#ubicacion y nombre del wallet descomprimido
COPY Wallet_NGQF61MG4RHT4YZR /app/oracle_wallet

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
