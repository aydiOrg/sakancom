Feature: tenant's data
  Description: the tenant can view and edit his data
  Actor: tenant

Scenario:
  Given user is logged in and the user is a tenant
  And the user selects the profile from the menu

  Then his personal data should be shown

Scenario:
  Given the user who is tenant in the profile and presses edit
  And user presses save after editing the data

  Then his data should be updated