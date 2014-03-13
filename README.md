MComm
=====

Molecular Communication Project
================================

	Active propagation parameters

stepLengthX: It is defined as the direction taken by the molecule in the given unit time on X-axis.
 
stepLengthY: It is defined as the direction taken by the molecule in the given unit time on Y-axis.
 
stepLengthZ: It is defined as the direction taken by the molecule in the given unit time on Z-axis.
  
mediumDimensionX: It is defined as the length of X dimension.
	
mediumDimensionY: It is defined as the length of Y dimension.
	
mediumDimensionZ: It is defined as the length of Z dimension.
	
maxSimulationTime: It is given as the *real* time taken or the time calculated for the simulation of the whole system per molecule.
                   The time is measured in terms of nano seconds. A value of -1 causes this to disabled and not be the limiting factor but
                   maxSimulationSteps is used exclusively
maxSimulationSteps: It is the number of "time" increments or steps the the simulation should run for. Simultion stops when this number is reached.
	                Thus is the "virtual simulation time".  This can be used exlusively or along with real (system) time limitation.
distSendReciever: This is the distance between the Sender and the Receiver.
	
*** Considering the distance between the sender and receiver to be 'd' and  considering the origin or the center, the distance between the sender and  
    center is 'd/2' and distance between center and receiver is 'd/2'. The molecule propagated can be on the rail or it can be outside the rail in the 
    given boundary of the medium where molecules are trying to communicate.
    Sender is considered to be a sphere , but a point in the present project.
    Both center and radius are taken into consideration for Receiver.
    For getting or assigning the position of the molecule which is derailed or which is not on the rail then the new position of the molecule coordinated
    should be within the following coordinates boundary.
    
  x-coordinate [-mediumDimensionX/2 , mediumDimensionX/2]
  y-coordinate [-mediumDimensionY/2 , mediumDimensionY/2]
  z-coordinate [-mediumDimensionZ/2 , mediumDimensionZ/2]
  
  The following are the constraints which satisfies the above boundaries
  senderPosition (10,10,10)
  recieverCenter (20,20,20)
  recieverRadius 2

The following give information about the molecules
noOfMolecules 10: 10 molecules are been propagated
stepLengthX 1: moves a unit length in x direction 
stepLengthY 1: moves a unit length in y direction 
stepLengthZ 1: moves a unit length in z direction 

velRail: It is defined as the distance propagated by a molecule along the rail in a given unit time.
probDrail: This is defined as the probability of derailing of a molecule from the rail.

The following parameters are considered to be important when considering microtubule
plusEndCentre and minusEndCentre are to be considered as the microtubule shows polarity + and -. Depending on the type of molecule the molecule might 
transfer from plus to minus or minus to plus. This determines the direction of the molecule.
plusEndCentre ()
minusEndCentre ()
radiusMicroTubule 
It exhibits a property of multiple microtubules.
If the user enters one set of values for plusEndCentre () minusEndCentre () radiusMicroTubule , then it is considered as 1 microtubule in the medium.
If the user enters second set of values then it is considered as 2 microtubules and so on.



















Contributors:

Chinmay, Sourav,Ruhavi, Tyrone, Narayanan.
