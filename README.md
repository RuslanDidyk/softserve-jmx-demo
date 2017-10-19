# softserve-jmx-demo
## Introduction to Java Management Extensions (JMX)

### jmx-bean
Run 'mvn clean install' to install dependency

### jmx-server
Run Application.java with VM args: -Dcom.sun.management.jmxremote -Dcom.sun.management.jmxremote.port=9990 -Dcom.sun.management.jmxremote.authenticate=false -Dcom.sun.management.jmxremote.ssl=false

### jmx-client
Run Application.java with Program args: --jmx.connection=service:jmx:rmi:///jndi/rmi://localhost:9990/jmxrmi
