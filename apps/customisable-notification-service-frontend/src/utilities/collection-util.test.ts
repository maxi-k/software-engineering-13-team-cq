import { transformObject, pluck, pick } from './collection-util'

describe('transformObject works as expected', () => {
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

  it("removes entries when there's a function in the transformationMap which returns undefined", () => {
    expect(transformObject(testObject, {
      key1: (value1: any) => (['key1', undefined] as [string, string])
    })).toEqual({
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

  it('adds entries from the initialization map to the object', () => {
    expect(transformObject(testObject, {}, {
      newlyAddedKey: 'value'
    })).toEqual({
      ...testObject,
      newlyAddedKey: 'value'
    })
  })
})

describe('pluck works as expected', () => {
  const testObject = {
    key1: 'value1',
    key2: 2,
    'key3': { my: 'object' },
    10: 0
  }
  it('plucks values from an object', () => {
    expect(pluck(testObject, ['key1', 'key3', 10])).toEqual([
      'value1', { my: 'object' }, 0
    ])
  })
})

describe('pick works as expected', () => {
  const testObject = {
    key1: 'value1',
    key2: 2,
    'key3': { my: 'object' },
    10: 0,
    noKey: 42
  }

  it('picks values from an object', () => {
    expect(pick(testObject, 'key1', 'key3', 10)).toEqual({
      key1: 'value1',
      key3: { my: 'object' },
      10: 0
    })
  })

  it('picks nothing with an empty key list', () => {
    expect(pick(testObject)).toEqual({})
  })
})
