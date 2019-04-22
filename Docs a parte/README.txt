Assumptions:

- The attribute showing as "maximumDeadline" is a mistake and it will be "maximumSalary" as the lecturer said.
- Problems and positions can ONLY be deleted if they are NOT in final mode (finalMode == false) in order to prevent errors caused due to one of them two being final and the other one not, and causing troubles. Also, we assume that something "final" is really "final" and shouldn't be deleted.
- When making performance tests we assumed that the user is already logged in. However the login and logout functions are tested properly anyways in other test.