# Frontend Development

This is a short guide to the development using the frontend stack we
decided for, with focus on the used patterns and code, and short
explanations as to why we made certain decisions.

For more general stuff focused on tooling and external guides and
references, please read [The Frontend README](../../frontend/README.md).

## Typescript, React and Props

### Props Basics

`Props` are the things passed between react components.
They can be passed between components like this:

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
