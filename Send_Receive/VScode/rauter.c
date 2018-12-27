#include <sys/types.h>
#include <netinet/in.h>
#include <inttypes.h>
#include <arpa/inet.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <strings.h>
#include <unistd.h>
#include <stdio.h>
#include <string.h>
#include <time.h>
#include <stdlib.h>


int main(int argc, char *argv[])
{
  int socket_fd, cc;
  socklen_t fsize;
  struct sockaddr_in  s_in, From,Client,Server;
  
  socket_fd = socket (AF_INET, SOCK_DGRAM, 0);  //in-socket
 // sockaddr_in
  memset((char *) &s_in,0, sizeof(s_in));  /* They say you must do this    */

  s_in.sin_family = (short)AF_INET;
  s_in.sin_addr.s_addr = htonl(INADDR_ANY);    
  s_in.sin_port = htons((u_short)0x3333);
// sockaddr_in - server
  memset((char *) &Server,0, sizeof(Server)); /* They say you must do this    */

  Server.sin_family = (short) AF_INET;
  Server.sin_addr.s_addr = htonl(INADDR_ANY);
  Server.sin_port = htons((u_short)0x3311);
// sockaddr_in - client
  memset((char *) &Client,0, sizeof(Client)); /* They say you must do this    */
  
  Client.sin_family = (short) AF_INET;
  Client.sin_addr.s_addr = htonl(INADDR_ANY);
  Client.sin_port = htons((u_short)0x3322);  

//bind for our socket
  bind(socket_fd, (struct sockaddr *)&s_in, sizeof(s_in));
    

  char msg_get[500];
  memset(&msg_get,0,sizeof(msg_get));
  while(1) {
    fsize = sizeof(From);
    cc = recvfrom(socket_fd,&msg_get,sizeof(msg_get),0,(struct sockaddr *)&From,&fsize);  //
    msg_get[cc] = '\0';

    if(From.sin_port==Client.sin_port){  // if client send to router by port
		     
	  double rnd = rand() % 2;
    double x = atof(argv[1]);
        	if(rnd > x){  //send client message       
	           	sendto(socket_fd, msg_get, strlen(msg_get) , 0, (struct sockaddr *)&Server,sizeof(Server));
	             }
	        else          //dont send..
          {   sendto(socket_fd, "Don't_Write", strlen("Don't_Write") , 0, (struct sockaddr *)&Server,sizeof(Server)); }
    }
    else{   //Always sending
	        sendto(socket_fd, msg_get , strlen(msg_get) , 0, (struct sockaddr *)&Client,sizeof(Client));       
     }

    memset(&msg_get,0,sizeof(msg_get));
  }
  
  return 0;
}
