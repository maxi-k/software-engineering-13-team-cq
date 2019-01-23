// Allow bitwise operations here in the linter,
// as this is want we want
/* tslint:disable:no-bitwise */
export const createUUID = () => {
  let seed = new Date().getTime();
  const uuid = 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, (character) => {
    const randomizer = (seed + Math.random() * 16) % 16 | 0;
    seed = Math.floor(seed / 16);
    /* tslint:disable */
    return (character === 'x'
      ? randomizer
      : (randomizer & 0x3 | 0x8)
    ).toString(16);
  });
  return uuid;
}
/* tslint:enable */
