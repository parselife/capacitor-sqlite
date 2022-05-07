import { WebPlugin } from '@capacitor/core';

import type { SqlitePluginPlugin } from './definitions';

export class SqlitePluginWeb extends WebPlugin implements SqlitePluginPlugin {
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
