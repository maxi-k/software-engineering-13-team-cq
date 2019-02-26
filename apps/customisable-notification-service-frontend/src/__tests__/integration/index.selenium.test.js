/**
 * @jest-environment jest-environment-webdriver
 */
const { createRandomKey } = require('@/services/identifier-service')

// Code after
// https://medium.com/@mathieux51/jest-selenium-webdriver-e25604969c6
// https://blog.evantahler.com/testing-javascript-applications-with-selenium-async-await-and-jest-7580ed074f2b

// require('geckodriver')
const url = 'http://localhost:3000'
const waitUntilTime = 1000 * 30

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

    it('makes it possible to add a new rule', async () => {
        await browser.get(url)
        /* Find the 'new rule' button */
        const element = await awaitElement(byTestId('add-rule-option'))
        element.click()
        /* Wait until we are in the creation view */
        const ruleCreationView = await awaitElement(byTestId('rule-creation-view'))
        /* Now, the rule fields should be immediately visible */
        const nameField = await browser.findElement(byTestId('rule-modification-field-name'))
        const descriptionField = await browser.findElement(byTestId('rule-modification-field-description'))
        /* Enter a description and a rule name */
        const uniqueRuleName = 'rule-' + createRandomKey()
        nameField.findElement(by.css('input')).sendKeys(uniqueRuleName)
        descriptionField.findElement(by.css('textarea')).sendKeys('rule description')

        /* Go to the next page: fleets */
        const nextButton = await awaitElement(byTestId('rule-modification-action-next'))
        nextButton.click()
        /* Go to the next page: condition */
        nextButton.click()
        /* Enter a value into the comparison constant */
        const comparisonConstantField = await awaitElement(byTestId(
            'rule-modification-condition-field-comparison-constant'
        ))
        comparisonConstantField.sendKeys('0.8')
        /* Wait until value has reached the redux store */
        await new Promise(resolve => {setTimeout(resolve, 500)})

        /* Go to the next page: aggregator */
        nextButton.click()

        /* Select a scheduled aggregator */
        const scheduledAggregatorSelector = await awaitElement(byTestId(
            'rule-modification-aggregator-strategy-selector-scheduled'
        ))
        scheduledAggregatorSelector.click()
        /* Set the time of day */
        const timeOfDaySelector = await awaitElement(byTestId(
            'rule-modification-aggregator-strategy-scheduled-time'
        ))
        timeOfDaySelector.findElement(by.css('input')).sendKeys('0420')
        /* The time of day should be reflected in the human readable cron field */
        const resultingCronStringElement = await awaitElement(byTestId(
            'rule-modification-aggregator-strategy-cron-string'
        ))
        const resultingCronString = await resultingCronStringElement.getText()
        expect(resultingCronString).toContain('04:20')

        /* Go to the next page: summary */
        nextButton.click()

        /* Wait for the rule detail view to appear */
        await awaitElement(byTestId('rule-details-view'))
        let pageSource = await browser.getPageSource()

        /* The summary should contain some text with the rule name */
        expect(pageSource).toContain(uniqueRuleName)

        /* Finish the rule creation */
        const completeButton = await awaitElement(byTestId('rule-modification-action-complete'))
        completeButton.click()

        /* We are back on the overview page with the newRuleTile button */
        const newRuleTile = await awaitElement(byTestId('add-rule-option'))
        pageSource = await browser.getPageSource()

        /* There is some representation of the rule name on the overview page */
        expect(browser.getPageSource()).toContain(uniqueRuleName)
    }, waitUntilTime * 10)
})
