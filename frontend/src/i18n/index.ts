const bmw_messages_de = require('./fleetdata-messages/de.json');
const bmw_messages_en = require('./fleetdata-messages/en.json');

const cns_messages_de = require('./cns-messages/de.json');
const cns_messages_en = require('./cns-messages/en.json');

export const messages = {
  de: {...bmw_messages_de, ...cns_messages_de},
  en: {...bmw_messages_en, ...cns_messages_en},
};
