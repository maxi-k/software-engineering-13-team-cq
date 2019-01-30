import { transformObject } from './collection-util'

const testObject = {
  key1: 'value1',
  numberKey2: 2,
  nestedObjectKey: {
    noKey1: 'noValue1',
    noKey2: []
  }
}

it('does not transform objects with out an objectMap', () => {
  expect(transformObject(testObject, {})).toEqual(testObject)
})

it("transforms entries when there's a string in the transformationMap", () => {
  expect(transformObject(testObject, { key1: 'newKey1' })).toEqual({
    newKey1: 'value1',
    numberKey2: 2,
    nestedObjectKey: {
      noKey1: 'noValue1',
      noKey2: []
    }
  })
})

it("transforms entries when there's a function in the transformationMap", () => {
  expect(transformObject(testObject, {
    key1: (value1: any) => (['newKey1', (value1 as string) + 'new'] as [string, string])
  })).toEqual({
    newKey1: 'value1new',
    numberKey2: 2,
    nestedObjectKey: {
      noKey1: 'noValue1',
      noKey2: []
    }
  })
})

it("transforms entries with an array in the transformationMap", () => {
  expect(transformObject(testObject, {
    nestedObjectKey: [
      (value: any) => (['newKey1', (value.noKey1 as string) + 'NewValue'] as [string, string]),
      (value: any) => (['newKey2', (value as string)] as [string, string]),
      (value: any) => (['nestedObjectKey', {}] as [string, object])
    ]
  })).toEqual({
    key1: 'value1',
    numberKey2: 2,
    newKey1: 'noValue1NewValue',
    newKey2: testObject.nestedObjectKey,
    nestedObjectKey: {}
  })
})

it("transforms nestedEntries", () => {
  expect(transformObject(testObject, {
    key1: (value1: any) => (['newKey1', (value1 as string) + 'new'] as [string, string]),
    nestedObjectKey: (value1: any) => (['newNestedObjectKey', transformObject(value1, {
      noKey2: 'newNoKey2'
    })] as [string, object])
  })).toEqual({
    newKey1: 'value1new',
    numberKey2: 2,
    newNestedObjectKey: {
      noKey1: 'noValue1',
      newNoKey2: []
    }
  })
})
