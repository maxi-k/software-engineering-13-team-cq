# Frontend Project README

## Quick Development Guide
- After cloning, execute `yarn install` to fetch all available packages.
  - If you don't have yarn installed yet, execute `npm install -g yarn`.
  - If you don't have `npm` or `node` installed yet, look up how to do it on your system.
- Execute `yarn start` to start a local development server with hot reloading. Edit some files.
- Execute `yarn test` to execute the tests for the frontend .
- Execute `yarn storybook` to start storybook in a similar modus operandi as `yarn start`, with hot reloading etc. Edit or add stories in `src/stories` to showcase them in the storybook. This is used to develop the design for components independently of their role in the application and any logic.
- Execute `yarn build` to create a production-ready bundle of the frontend. This is usually not done manually, but instead by travis or heroku.

Have a look in `package.json` under the `scripts` section for more common commands.
If you find yourself executing something else commonly, consider adding it as a `script`.

## Used Packages
This frontend for the BMW CNS frontend uses
- TypeScript
- React
- Redux
- Storybook for component visualization
Have a look at `package.json` for a complete list

## Used Patterns / Conventions
- ~~[Atomic
  Design](http://bradfrost.com/blog/post/atomic-web-design/)~~
  Deprecated in favor of a domain-based modularization
- The [Redux Duck Pattern](https://github.com/erikras/ducks-modular-redux)

For a more high-level documentation on the code, explanations about used
practices and more, please go to [The Frontend
Documentation](../doc/technische-dokumentation/frontend-development.md).
**Please read that document for an overview of the followed guidelines
before developing.**

## Guides
- React + Redux + Typescript: https://github.com/piotrwitek/react-redux-typescript-guide
- Typescript with React:
  - https://www.typescriptlang.org/docs/handbook/jsx.html
  - https://medium.com/innovation-and-technology/deciphering-typescripts-react-errors-8704cc9ef402


# Standard create-react-app README

This project was bootstrapped with [Create React App](https://github.com/facebook/create-react-app).

## Available Scripts

In the project directory, you can run:

### `yarn start`

Runs the app in the development mode.<br>
Open [http://localhost:3000](http://localhost:3000) to view it in the browser.

The page will reload if you make edits.<br>
You will also see any lint errors in the console.

### `yarn test`

Launches the test runner in the interactive watch mode.<br>
See the section about [running tests](https://facebook.github.io/create-react-app/docs/running-tests) for more information.

### `yarn build`

Builds the app for production to the `build` folder.<br>
It correctly bundles React in production mode and optimizes the build for the best performance.

The build is minified and the filenames include the hashes.<br>
Your app is ready to be deployed!

See the section about [deployment](https://facebook.github.io/create-react-app/docs/deployment) for more information.

### `yarn eject`

**Note: this is a one-way operation. Once you `eject`, you can’t go back!**

If you aren’t satisfied with the build tool and configuration choices, you can `eject` at any time. This command will remove the single build dependency from your project.

Instead, it will copy all the configuration files and the transitive dependencies (Webpack, Babel, ESLint, etc) right into your project so you have full control over them. All of the commands except `eject` will still work, but they will point to the copied scripts so you can tweak them. At this point you’re on your own.

You don’t have to ever use `eject`. The curated feature set is suitable for small and middle deployments, and you shouldn’t feel obligated to use this feature. However we understand that this tool wouldn’t be useful if you couldn’t customize it when you are ready for it.

## Learn More

You can learn more in the [Create React App documentation](https://facebook.github.io/create-react-app/docs/getting-started).

To learn React, check out the [React documentation](https://reactjs.org/).
