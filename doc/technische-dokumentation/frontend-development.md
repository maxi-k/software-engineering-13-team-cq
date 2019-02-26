# Frontend Development

#### Table of Contents
[Running the Frontend](#running-the-frontend)

[Architecture](#architecture)

[Tests](#running-tests)
* [Types of Tests](#types-of-tests)
* [Running Tests](#running-tests)

[Conventions](#conventions)
* [General Conventions](#general-conventions)
* [Typescript, React and Props](#typescript-react-and-props)

This is a short guide to the development using the frontend stack we
decided for, with focus on the used patterns and code, and short
explanations as to why we made certain decisions.

For more general stuff focused on tooling and external guides and
references, please read [The Frontend README](../../apps/customisable-notification-service-frontend/README.md).

# Running the Frontend
Once you have started the backend and the required mocks as described in [The Docker Guide](./docker-development.md) 
(TL;DR: run `scripts/frontend-dev`), and installed the required packages using `yarn install`, start the frontend using one of the following commands:
- `yarn start`: Start the frontend development server with hot reloading
- `yarn storybook`: Start the storybook used for viewing single components and snapshot testing
- `yarn build`: Build the frontend into a production-ready format, output to `build/`
- `yarn build-storybook`: Build the storybook into a production-ready format, output to `build/`


# Tests
The frontend test coverage is not as good as the backend coverage, but still covers about 70% of the LOC at the time of this writing. The cns frontend uses [Jest](https://jestjs.io/) as the main testing framework, as well a variety of other testing tools.

There is also a linter, which checks coding style and catches simple coding errors.

## Types of Tests
There are three types of tests in the frontend:
- *Unit Tests*: Unit tests for single functions and logic. 
  These are mainly used to test the services.
- *Snapshot Tests*: Snapshot Tests done using [Storybook](https://storybook.js.org/), 
  used to render static views of different components with different configurations of 
  passed props.
- *Selenium Tests*: There are two selenium tests, one of which simply tests that the page is loadable in the browser.
  The other one goes through a full notification-rule creation process, and checks whether the rule was really created 
  and is getting displayed afterwards. Thus, it can be seen as a full-fledged integration test for frontend and backend.
  You can see a video of it [here](../integration-tests/selenium-rule-creation.mov).

## Running Tests
Looking in `package.json`, there are a lot of commands for running the available tests:
- *lint*: This runs the aforementioned linter
- *test*: This runs snapshot- and unit tests in an interactive mode
- *test-coverage*: This runs snapshot- and unit tests in an interactive mode, 
  generating test coverage reports in the process. If the test-coverage is not high enough
  (threshold defined in `package.json`), this will fail.
- *test-integration*: This does the same as *test-coverage*, but runs the selenium tests as well. 
  It requires the backend and the various mocks to be running, and will open a chrome window to click though the frontend.
- *test-commit*: This does the same as *test-coverage* but runs in CI mode. 
  It is added as a pre-push hook and thus ensures that all tests pass,
  and the coverage is high enough, before being able to push.


# Architecture
The architecture of the frontend is defined mainly by the usage of `react` and `redux`.
While `react` promotes a component-based architecture, `redux` is used for state-management and leads to a decoupled and functional programming style.
Besides this, there are some more project-specific things to be noted:

* *View Modules*: The frontend is divided into different view modules defined by the domain, 
  like `rule-modification`, `rule-detial`, `rule-overview`.
* *Ducks*: Loosely following the [Redux Duck Pattern](https://github.com/erikras/ducks-modular-redux), 
  there are `ducks` for each view module, as well as some more for behind-the-scenes things, 
  like authentication and fetching internal state data from the backend.
* *State*: The `duck`s are spread under the folder `state/`, and combined into a single redux state
  reducer under `state/index.ts`. The asynchronous fetching logic using es2015 [Generator Functions](https://developer.mozilla.org/de/docs/Web/JavaScript/Reference/Statements/function*)
  is also defined in the `ducks`s, and combined with [Redux Saga](https://redux-saga.js.org/) in the same file.
* *Services*: Most logic is either contained in the state management in the `duck`s under `state/`, 
  or in the various services under `services/`.
* *BMWs Modules*: The `fleetdata` modules are located under `fleetdata/`
* *I18n*: Internationalisation Files are located under *i18n/*. 
  This includes language switching logic and components from BMW, 
  as well as the internationalisation JSON Files.


# Conventions

## General Conventions

### Modules
The Frontend components are modularized based on the different parts of the
domain, like entity `modification`, `detail`, `overview` and so on, as
well as a `shared` module, which contains very general components used
by every module, like loading indicators.

Each module's components are divided into three subtypes:
- `components`: Basic Components which are based solely on their props
  and possibly internal state
- `parts`: Components which use external state, like from the `redux`
  store
- `views`: Components which may use external state, and define a
  coherent and usable interface to the user (not just parts of it).

These subtypes are reflected in the directory structure.

### Dependencies

As a rule of thumb, components from modules should only ever use
other components from their own module, or components from the
`shared` module, but not components from other modules.

Inside Modules, the dependency hierarchy should strictly follow the level of
abstraction, that is, `views` should depend on `parts` and basic `components`,
`parts` should depend on `components`, but `components` should never
depend on `parts`, and `parts` should never depend on `views`:

```
views -> parts -> components
```

### Imports

Imports can be absolute from the base level of the project using the
prefix `@/`, or relative. As a rule of thumb, you should
- Use *relative* imports when using components from your own module
- Use *absolute* imports when using components from other modules
- Use *absolute* imports for importing services, state and utilities.

Try to order imports in a somewhat consistent way, following this
order for import statements in the file:
- React (if required)
- Services and logic from third party libraries
- Services and logic from the project
- *Blank Line*
- React Components from third party libraries
- React Components from the `shared` module
- React Components from other modules
- React Components from your own module

There will be some unclear cases, like when a library provides a
Component which is not really a "display"-type component, but instead
more of a logical / functionality providing component (like `i18n`s
`FormattedMessage` or a `MediaQuery` component). You may place these
in the *servcies & logic* section as well as the *3rd party component*
section. In general, do what feels right.

If there are a lot of imports, insert more blank lines between the
bullet points above.

## Typescript, React and Props

### Props Basics

`Props` are the things passed between react components. They can be
passed between components like this:

```jsx
const MyComponent = ({ color, size }) => {
  return <LoadingIndicator type={'c-' + color + '-s-' + size} />
}
```
Here, the component `MyComponent` can receive the props `color` and
`size`,
while the `LoadingIndicator` prop is passed a prop `type`. Behind the
scenes, the function for the component `LoadingIndicator` will receive an object which
may look like this:
```js
{
    type: 'c-red-s-15'
}
```

Another pattern that is often applied, is using the spreading
operator for passing down props to used components in a flexible way,
for example:

```jsx
const MyComponent = ({ color, size, ...other }) => {
  return (
    <div {...other}>
      <LoadingIndicator type={'c-' + color + '-s-' + size} />
    </div>
  )
}
```
Here, the special props `color` and `size` are passed to
`LoadingIndicator`, while all other props (for example, `style`,
`className`, `id`, ...) are passed to the wrapping `div`

### Typescript and Prop Types

Looking at the above code in the context of TypeScript, we have to
immediately ask: What even is the signature of the function
`MyComponent`, or in other words, what is its type?

Because it's a React component, the React library itself provides a
bunch of types which can be used to describe the signature of the
function and its props. In this case, they are
- `React.SFC<T>` for the function signature, where `SFC` stands for
  *Stateless Functional Component*, and the generic parameter `<T>` is
  used to specify the type of the `props` it receives as an argument.
- Various types describing properties (the arguments of the function),
  such as `HTMLProps<T>`, `HTMLAttributes<T>` and more. `<T>` can be
  used to specify the Type of element the props are applied to, for
  example `HTMLDivElement`, `HTMLInputElement` and so on.

A first draft for the type of `MyComponent` could look like this:
```ts
const MyComponent: React.SFC<HTMLAttributes<HTMLDivElement>> = ({
color, size, ...other }) => {...}
```
However, `color` and `size` are not standard HTML attributes, and so
this is not correctly typed. The function should be able to receive
more props than specified by its signature. One possible solution is to replace
`HTMLAttributes<HTMLDivElement>` in the above declaration with an own
interface, such as
```ts
interface MyComponentProps
  extends HTMLAttributes<HTMLDivElement> {
  color: string,
  size: number
}
```
Using this interface works just fine, the code compiles.

However, by doing this, we have forever mixed the definition of the
interface for the properties `color` and `size` with the rest of the
HTML attributes; even worse, they are mixed with the specific HTML
attributes for a `div` element by using `HTMLDivElement`.

For this reason, we have decided to use the following pattern do
describe components which can take "normal" HTML props, plus some other
specific props:
- We define an `interface` just for the component-specific props called
  `MyComponentAttributes`
- We define a `type` that intersects the previously defined
  interface type with other required types, such as
  `HTMLAttributes` if necessary. We call this type
  `MyComponentProps`, as it describes the props the component can
  actually receive.

When done, it looks like this:
```ts
/* Define interface for component-specific props, call it *Attributes */
interface MyComonentAttributes {
    color: string,
    size: number
}

/* Define type for all component props by using type intersection (&) */
type MyComponentProps = MyComponentAttributes & HTMLAttributes<HTMLDivElement>

const MyComponent: React.SFC<MyComponentProps> = ({
    color, type, ...other}
    ) => (
    <div {...other}>
        <LoadingIndicator type={'c-' + color + '-s-' + size} />
    </div>
)
```

Of course `MyComponentProps` can intersect other and more types than
shown above, such as mixing in properties for asynchronous or error
states etc.

**TL;DR: Use the pattern from the last code snippet to define the
types of component props**
