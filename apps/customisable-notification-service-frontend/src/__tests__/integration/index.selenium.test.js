/**
 * @jest-environment jest-environment-webdriver
 */

// Code after
// https://medium.com/@mathieux51/jest-selenium-webdriver-e25604969c6
// https://blog.evantahler.com/testing-javascript-applications-with-selenium-async-await-and-jest-7580ed074f2b

// require('geckodriver')
const url = 'http://localhost:3000'
const waitUntilTime = 1000 * 60

async function awaitElement(byExpression) {
    const foundElement = await browser.wait(until.elementLocated(byExpression), waitUntilTime)
    return await browser.wait(until.elementIsVisible(foundElement), waitUntilTime)
}

const byTestId = (testId) => by.xpath(`//*[@data-test-id='${testId}']`)

describe('the index page', () => {

    it('contains a language switcher', async () => {
        await browser.get(url)
        const element = await browser.findElement(byTestId('language-switcher-en'))
        const expectedText = await element.getText()
        expect(expectedText).toContain('EN')
    })
})
