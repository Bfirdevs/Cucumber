Feature: HR Application Database and UI data verification
@HrAppDB
Scenario: Department data UI Database verification
Given I am on DeptEmpPage
When I search for Department id 10
And I query database with sql "SELECT department_name, manager_id, location_id from departments where department_id =10"
Then UI data and DATAbase must match
