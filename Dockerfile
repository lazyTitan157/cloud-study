FROM ubuntu
COPY ./install.sh /
RUN chmod 755 /install.sh
RUN /install.sh
CMD [ "nginx", "-g", "daemon off;"]
expose 80

COPY ./start.sh /
RUN chmod 755 /start.sh
CMD /start.sh
