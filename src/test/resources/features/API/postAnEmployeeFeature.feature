
  Feature: Employee REST Api requests
    @ApiPost
    Scenario:
      Given Content type and Accept type is Json
      When I post a new Employee with "random" id
      Then Status code is 201
      And Response Json should contain Employee info
      When I send a Get request with "random" id
      Then status code is 200
      And employee Json Response Data should match the posted Json Data
