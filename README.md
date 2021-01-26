# challenge_solutionCSGL
Challenge DWS

This project solves provided challenge where the transfer operation has to be created
Considerations when taking a look to the solution:

   · @Transactional is used as reference as i consider some TransactionManager implementation would be useful in this scope to prevent deadlocks.
   More information about what i refer to: https://www.baeldung.com/transaction-configuration-with-jpa-and-spring
   
   · I've considered a transfer between accounts as a new item that would be useful to maybe be saved or audited, so i will consider the operation GET /v1/accounts/{accountId}/transfers to exist at some point
   
Regards
 
 


