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

    int soc = socket(AF_INET, SOCK_STREAM, 0);
    if(soc == -1) error("Couldn't create socket");

    int portno = atoi(argv[2]);

    struct sockaddr_in server_addr;
    server_addr.sin_family = AF_INET;
    server_addr.sin_port = htons(portno);
 
    int Connect = connect(soc, (struct sockaddr *) &server_addr, sizeof(server_addr));
    if(Connect == -1) {
        error("Error while connecting");
    } 

    char msg[] = "Hi from Client";
    int n = send(soc, msg, sizeof(msg), 0);

    char server_response[2048];
    int REC = recv(soc, &server_response, sizeof(server_response), 0);
    if(REC == -1) error("cant rec the mssge from server");

    printf("Server: %s\n", server_response);

    return 0;
}