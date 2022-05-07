import { registerPlugin } from '@capacitor/core';

import type { SqlitePluginPlugin } from './definitions';

const SqlitePlugin = registerPlugin<SqlitePluginPlugin>('SqlitePlugin', {
  web: () => import('./web').then(m => new m.SqlitePluginWeb()),
});

export * from './definitions';
export { SqlitePlugin };
