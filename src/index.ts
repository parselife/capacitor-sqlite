/*
 * @Author: alex
 * @Date: 2022-05-07 13:53:50
 * @LastEditTime: 2022-05-11 16:00:27
 * @LastEditors: alex
 */
import { registerPlugin } from '@capacitor/core';

import type { CapacitorSqlitePlugin } from './definitions';

const SqlitePlugin = registerPlugin<CapacitorSqlitePlugin>('SqlitePlugin', {
  web: () => import('./web').then(m => new m.SqlitePluginWeb()),
});

export * from './definitions';
export { SqlitePlugin };
