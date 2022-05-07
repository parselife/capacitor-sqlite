export interface SqlitePluginPlugin {
  echo(options: { value: string }): Promise<{ value: string }>;
}
