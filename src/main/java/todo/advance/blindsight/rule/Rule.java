package todo.advance.blindsight.rule;

public class Rule {

    /**
     * ! DO NOT CHANGE CUSTOMER REQUIREMENT UNLESS YOU MUST DISCUSS WITH THEM
     * 
     * * This is requirement from customer
     *
     * & CREATE & BUILD TASK PROGRAM 
     *
     * ^ Technology using: 
     * ? Backend: JAVA SPRINGBOOT
     * ? Database: POSTGRESQL, MONGODB
     * ? Api: REST, GRAPHQL
     * ? Git: Gitlab
     * ? Open source: cloudinary
     * 
     * & REQUIREMENT:
     *
     * * The customer want to create task program that have account, profile. 
     * * In this program, the customer want create task with big task's card outside.
     * * Inside task's card has subcard and inside subcard will have todo. 
     * * One card will have many subcards inside and one subcard will have many todos.
     * * When user registered they will see their account but they don't have any task yet.
     * * account will be registerd by email & password. Every account will only have one email. 
     * * if 2 account is using same email the system will alert and don't let user create account.
     * * After that they will create their profile if they don't have account they can't create profile.
     * * The profile will have avatar, name, bio & dateJoin.
     * * When created account the system will automatically set role for account is guest.
     * * when account is updated to another role, the user now have permission to do task now.
     * *
     * * Role will be seperated to 7 path (highest to lowest):
     * * 1. Admin
     * * 2. Owner
     * * 3. Leader
     * * 4. Employee
     * * 5. Maintainer
     * * 6. Reporter
     * * 7. Guest
     * *
     * * one account will have many roles
     * * 
     * * Card will have name, image, time created
     * * SubCard will have name, description, time upload and duedate
     * * Todo will have todo description and check box if todo is done or not
     * * One profile can have many tasks and one task can have many profiles
     * *
     * * for function:
     * * create account
     * * login account
     * * * send email verification
     * * send reset email
     * * * reset password with code OTP
     * * create profile
     * * update profile
     * * update profile with task
     * * delete profile also delete account
     * * search task
     * * search profile
     * * crud card
     * * crud subcard
     * * crud todo
     * 
     * ^ SOME QUERIES FOR THIS APP
     * * 1. Retrieve all data from any table in database
     * * 2. Retrieve profiles along with their associated account details
     * * 3. Retrieve all profiles with their respective sub-cards
     * * 4. Retrieve the number of todos for each profile
     * * 5. Retrieve profiles with their associated roles
     * 
     * 
     */
}
