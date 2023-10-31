#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#include <sys/types.h>
#include <sys/socket.h>
#include <netinet/in.h>
#include <unistd.h>

void error(char *err_msg) {
    perror(err_msg);
    exit(EXIT_FAILURE);
}


int main(int argc, char *argv[]) {
    
    int socketFD = socket(AF_INET, SOCK_STREAM, 0);
    int portno = atoi(argv[1]);

    struct sockaddr_in server_addr;
    server_addr.sin_addr.s_addr = INADDR_ANY;
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(portno);

    int Bind = bind(socketFD, (struct sockaddr *) &server_addr, sizeof(server_addr));
    if(Bind == -1) {
        error("Failed to bind to the port");
    }

    listen(socketFD, 1);

    int clientFD = accept(socketFD, NULL, NULL);
    if(clientFD == -1) {
        error("Failed to accept");
    }

    char buffer[2048];

    int n = read(clientFD, buffer, sizeof(buffer));
    if(n == -1) error("Error while reading");

    printf("Client: %s\n", buffer);

    char msg[] = "Hi from Server";

    n = send(clientFD, msg, sizeof(msg), 0);
    close(socketFD);
    return 0;
}