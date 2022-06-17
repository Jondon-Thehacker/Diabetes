describe('Test2.cy.js', () => {
  it('The user tries to view a list of his own patients, but there are none', () => {
    cy.visit('http://localhost:8081/')

    cy.wait(5000)
    cy.get('.list-group').within(($list) => {
      
      cy.get('.list-group-item').should('not.exist');

    })
  })
})
