const bmwMessagesDe = require('./fleetdata-messages/de.json');
const bmwMessagesEn = require('./fleetdata-messages/en.json');

const cnsMessagesDe = require('./cns-messages/de.json');
const cnsMessagesEn = require('./cns-messages/en.json');

export const messages = {
  de: {...bmwMessagesDe, ...cnsMessagesDe},
  en: {...bmwMessagesEn, ...cnsMessagesEn},
};
