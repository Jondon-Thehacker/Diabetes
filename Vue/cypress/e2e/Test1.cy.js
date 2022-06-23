describe('Test1.cy.js', () => {
  it('The user successfully views a list of his of own patients', () => {
    cy.visit('http://localhost:8081/')

    let countOfElements = 0
    cy.get('.list-group-item').then($elements => {
      countOfElements = $elements.length
    })

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients'
    }).then(({ body }) => {
      expect(body).to.have.length(countOfElements)
    })

    cy.contains('Lesley Peacock')
    cy.contains('Laryn Bush')
    cy.contains('Marjory Tuft')
    cy.contains('Kelsey Ragnvaldsson')
    cy.contains('Stacia Hunnisett')

  })

  it('The user successfully views a patient\'s profile', () => {
    cy.visit('http://localhost:8081/')

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients',
    }).then(({ body }) => {
      cy.contains(body[0].patientName).click()
    })

    cy.contains('Lesley Peacock').click()
  })

  it('The doctors successfully views patient data', () => {
    cy.visit('http://localhost:8081/')

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients',
    }).then(({ body }) => {
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

  it('The doctor successfully posts a note', () => {

    cy.visit('http://localhost:8081/')

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients'
    }).then(({ body }) => { //Skal være inde i samme scope
      cy.contains(body[0].patientName).click()
      let patient = body[0].patientName

      cy.contains('Toggle notes').click()
      cy.contains('Add a note').click()

      cy.get('.modal-content').should('be.visible')
      cy.get('textarea', { timeout: 10000 }).eq(0)
        .click().type('Testing for patient: ' + patient)
      cy.get('.modal-footer').contains('Add note').click({ force: true })
    })
  })

  it('The doctor successfully deletes a note', () => {

    cy.visit('http://localhost:8081/')

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients'
    }).then(({ body }) => {
      cy.contains(body[0].patientName).click()
      let patient = body[0].patientName

      cy.contains('Toggle notes').click()


      cy.get('.delete-note-btn').last().click()
      cy.wait(100)
      cy.get('.modal-content').should('be.visible')
      cy.contains('Yes').last().click({ force: true })
    })
  })


  it('The user successfully views a note associated with his own patient', () => {
    cy.visit('http://localhost:8081/')

    cy.request({
      method: 'GET',
      url: 'http://localhost:8080/api/v1/Doctors/0/patients'
    }).then(({ body }) => {
      cy.contains(body[1].patientName).click()
      let patient = body[1].patientName

      cy.contains('Toggle notes').click()

      cy.contains('DoctorId: 1')
    })
  })
})
