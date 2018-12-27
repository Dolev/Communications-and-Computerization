#include <sys/types.h>
#include <netinet/in.h>
#include <inttypes.h>
#include <netdb.h>
#include <sys/types.h>
#include <sys/socket.h>
#include <strings.h>
#include <unistd.h>
#include <string.h>
#include <stdio.h>

int main(int argc, char *argv[])
{
  int socket_client; // define a socket 
  struct sockaddr_in  dest;// build a struct that contains communication protocol,IP address,Port,data to send.
  struct hostent *hostptr; //defines a pointer to the struct 
  struct { char head; char body[100]; char* X; char tail; } msgbuf; //defines a struct that contains a head char ,u_long body,char tail.

  socket_client = socket (AF_INET, SOCK_DGRAM, 0); // Initializ the socket with the IP protocol,data transfe protocol.
  memset((char *) &dest,0, sizeof(dest)); /* They say you must do this */ // Initializa block of memory with a given value.
  hostptr = gethostbyname(argv[1]);  //points on the destinaion IP.
  dest.sin_family = (short) AF_INET; // puts the IP protocol in the destinaion IP protocol.
  memcpy(hostptr->h_addr, (char *)&dest.sin_addr,hostptr->h_length); //copies the host IP address.
  dest.sin_port = htons((u_short)0x3333); //sets the destination port.

  
  msgbuf.head = '<';
 memcpy(msgbuf.body,argv[2],sizeof(argv[2])); 
 memcpy (msgbuf.X,argv[3],sizeof(argv[3]));
 // msgbuf.body = htonl(getpid()); /* IMPORTANT! */ 
  msgbuf.tail = '>';
  int from=sizeof(dest);
  sendto(socket_fd,&msgbuf,sizeof(msgbuf),0,(struct sockaddr *)&dest,sizeof(dest)); // sends the messege to the destination.
  recvfrom(socket_fd,&msgbuf,sizeof(msgbuf),0,(struct sockaddr *)&dest,&from);
  printf("got data:%c%s%c\n",msgbuf.head,msgbuf.body,msgbuf.tail);


  return 0;
}