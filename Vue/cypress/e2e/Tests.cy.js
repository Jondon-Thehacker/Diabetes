describe('Tests.cy.js', () => {
  it('SelectPatientProfile', () => {
    cy.visit('http://localhost:8081/')

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients',
    }).then(({body}) =>{
      cy.contains(body[0].patientName).click()
    })

    cy.contains('Lesley Peacock').click()
  })

  it('ViewDataVisualized', () => {
    cy.visit('http://localhost:8081/')

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients',
    }).then(({body}) =>{
      cy.contains(body[0].patientName).click()
      
    })
      cy.contains('Choose Visualization ').click()
      cy.contains('General Information').click()
      cy.contains('Choose type').click()
      cy.contains('CGM').click()
      cy.contains('Select Interval').click()
      cy.get('.modal-body').should('be.visible')

      cy.get('#startDate').type('2022-01-01')
      cy.get('#startTime').type('00:00')

      cy.get('#endDate').type('2022-01-03')
      cy.get('#endTime').type('00:00')

      cy.contains('Save changes').click()
  })

  it('ViewList', () => {
    cy.visit('http://localhost:8081/')

    let countOfElements = 0
    cy.get('.list-group-item').then($elements => {
      countOfElements = $elements.length
    })

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients'
    }).then(({body})=> {
      expect(body).to.have.length(countOfElements)
    }) 

    cy.contains('Lesley Peacock')
    cy.contains('Laryn Bush')
    cy.contains('Marjory Tuft')
    cy.contains('Kelsey Ragnvaldsson')
    cy.contains('Stacia Hunnisett')

  })


  it('WriteMessagesToOwnPatients', () => {
    let patient = ''
    cy.visit('http://localhost:8081/')

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients'
    }).then(({body})=> {
      cy.contains(body[0].patientName).click()
      patient = body[0].patientName
    }) 

    cy.contains('Toggle notes').click()
    cy.contains('Add a note').click()
    cy.get('#writeNote').type('Looks good '+ patient)

  })

  
  

})
