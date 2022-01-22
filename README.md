
# Contract App

Managing contracts is not an easy task, believe me, we know how that can be complex. They have specific terms, cancellation and prolongation dates and it’s quite easy to lose track of all the details.
<br/>

## Objective:
To help users to have an overview about their contracts, by creating a small application where users will be able to add contracts and manage them.
<br/><br/>

## Features

- Account creation and sign into existing accounts

- Contracts CRUD using Firestore
<br/><br/>

## Project Structure


### Sign Up

- If a new User the user signup's with his credintials and recieves a verification mail on this given mail id.
- He can also sign up with google-sign-in
<br/>


### Login

- A registered user enters his credentials and get redirected to the dashboard.
 <br/>



### Dashboard

The list is sorted by the **Ends on** value (ascending).

- If the __Ends on__ value is in the past, the contract is not shown in the list.

- When users click on the __contract item__, they are redirected to the “view contract” page, showing the details of the selected contract.

- Users can only access contracts that belongs to them.

- If users click on the __Add contract__ button, then they are redirected to the “Add contract” page. 
<br/>




### Add Contract Activity

- Add the vending activity details.
<br/>

### Details Contract Activity

- The user can only view the vending activity in detail.
- Here he has 2 options either to edit or delete the activity.
- If clicks delete, he is shown a confirmation whether to delete. If yes, he is redirected to the dashboard where the Upcoming contracts page gets updated in sorted order.
- If clicks edit he is redirected to the edit activity.
<br/>

### Edit Contract Activity

- Edit the vending details.
- If changes are made, redirected to dashboard and upcoming contracts get updated with th edited one.
