# PDS
A product Delivery System that uses RMI, a distributed computing technology.

For the E-commerce company CFK Group Malaysia, the main aim of the proposed solution is to develop a Product delivery system that is easy to use and that eliminates present inadequacies and allows the company to take control of delivery operations, enhance income, and delight consumers all from a single, simple platform. The suggestion is to develop a system using RMI, a distributed computing technology. Which involves writing both the server and the client program.

----Introduction

The CKF Group Malaysia, or simply CKF, is a global corporation that concentrates mostly on e-commerce. Through its e-commerce platforms, the e-commerce company is well known for its top-selling categories such as Baby goods, Accessories, Household Appliances, and Cell phones. The company now employs a PDS (Product Delivery System) to handle product delivery, but they believe that the system does not appear to be an easy-to-use platform and that it is unable to handle product delivery as demand for products grows daily. As a result, they would like a PDS (Product Delivery System) that eliminates present inadequacies and allows them to take control of delivery operations, enhance income, and delight consumers all from a single, simple platform.

----Problem Statement

The CKF E-commerce company faces problems that need to be addressed in the current proposed solution. The problem statement is stated below:

•	The e-commerce company’s product delivery platform did not appear to be an easy-to-use-platform

•	It’s unable to handle product delivery as demand for products increased day by day

•	Limited functionalities available

•	Lack of security

•	Components of the system are located only on one networked computer

•	Lack of distributed application

--------- Requirements

---- Functional Requirements

•	Allow Customer to register for the first time using first name, last name, IC/Passport number, username, and password.

•	Show a message if the IC/Passport number or username already exists in the system.

•	Allow a customer to sign in 

•	Allow a customer to order the available products

•	Allow a customer to view the report generated of their orders


•	Allow a customer to checkout

•	Allow a customer to view all available products 

•	Allow a customer to add a product to their cart

•	Allow a customer to logout

•	Allow a customer to exit the program


---Software Requirements

The needed software requirements for the proposed system are:


•	Microsoft word (Documentation)

•	IntelliJ/Netbeans (Programming)

•	TeamGant (Develop a gantt chart)


--------Hardware Requirements


The needed hardware requirements include:

•	RAM – 4GB

•	Monitor, mouse, and a keyboard

-----Concepts Implemented 

---RMI (Remote Method Invocation)

RMI is used to construct distributed applications; it offers Java-program remote communication where it is included in the java.rmi. package.

Two programs are developed in an RMI Application; one is the client and the other is the server.

•	Server Program, where a remote object is created inside, and the client has a reference of this object. This is done by using a registry.

•	The client side queries remote server objects and tries to call their methods.

The developers have utilized the concept of RMI and introduced a system that involves a client-side and server-side model. The client is involved in the frontend tasks of the program such as, getting input from the user, and displaying appropriate information to the user. While the server-side does all the backend tasks of the system such as add records to the database and validates user information with the database. There are 5 interfaces within the system utilized to achieve Remote Method Invocation.


This shows the usage of an RMI service. The class Naming has methods that store and get the remote objects. The client gets the stub-object using the naming class which contains the lookup() function calling the object method. We run the server and client programs on the same device and therefore we use localhost in this project. 

The server class extends a unicast remote object that defines an object which is non-replicable. The classes references are only valid when the server process is alive. 

----- Serialization

Serialization is the process of transforming an object's state into a stream of bytes. An object is serializable if they implement the interface (java.io.Serializable) Deserialization is the process of reversing where  stream of bytes are used to remake the original Java object in memory.

Serialization was implemented in this project to store the information and the state of cart data of each user, they can be able to exit the system and log in and still find their cart in the state they left in. When a user logs in their relevant serialized data is retrieved and converted back to objects, and therefore added back to the cart.


For implementing serialization and deserialization, two methods were introduced. The SerilizeData method at first creates a file using a file output stream which is unique for each user as the name of the file relies on the user’s username. An object output stream is then introduced to write to be able to write on the created file. The cartProducts array which contains all the products in a user’s cart is then passed to the file in a serialized format. The Deserialize Data method works in the opposite way, it first checks whether if the file that is being looked for exists in the system. If it exists, then the file is read object input stream and the read data is deserialized before storing it back to the cartProducts array. Once it is done, the file and object input stream are terminated.

------ Multithreading

Multithreading is a concept in Java to simultaneously execute more than two threads to maximize CPU use. Two or more threads occur concurrently with multithreaded programs. Thus, is also known in java as concurrency. Every thread runs side by side. There is no separate memory space for multi-threads, therefore resulting in the saving of memory. (Great Learninng Team, 2021). It also takes comparatively less time to switch the context between threads. The developers have utilized multi-threading by making the main class that is involved to start the server a thread and then triggering that thread. The snippet of the main class below shows the implementation of multithreading.

