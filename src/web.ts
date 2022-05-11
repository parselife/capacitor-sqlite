/*
 * @Author: alex
 * @Date: 2022-05-07 13:53:50
 * @LastEditTime: 2022-05-11 16:06:42
 * @LastEditors: alex
 */
import { WebPlugin } from '@capacitor/core';

import type {
  CapacitorSqlitePlugin,
  SQLiteQueryOptions,
  SQLiteEnitytOptions,
  SQLiteEntityUpdateOptions,
} from './definitions';

export class SqlitePluginWeb
  extends WebPlugin
  implements CapacitorSqlitePlugin {
  async updateEntity(
    options: SQLiteEntityUpdateOptions,
  ): Promise<{ value: string | boolean }> {
    console.log(options);
    return { value: 'not supported' };
  }
  async insertEntity(
    options: SQLiteEnitytOptions,
  ): Promise<{ value: string | boolean }> {
    console.log(options);
    return { value: 'not supported' };
  }

  async queryForList(
    options: SQLiteQueryOptions,
  ): Promise<{ value: Record<string, unknown>[] }> {
    console.log(options);
    return { value: [{ data: 'not supported' }] };
  }

  async queryForObject(
    options: SQLiteQueryOptions,
  ): Promise<{ value: Record<string, unknown> }> {
    console.log(options);
    return { value: { data: 'not supported' } };
  }

  async loadDatabase(options: {
    dbPath: string;
    dbName: string;
  }): Promise<{ value: string | boolean }> {
    console.log(options);
    return { value: 'not supported' };
  }

  /**
   * 测试方法
   * @param options 参数
   * @returns 相同值
   */
  async echo(options: { value: string }): Promise<{ value: string }> {
    console.log('ECHO', options);
    return options;
  }
}
