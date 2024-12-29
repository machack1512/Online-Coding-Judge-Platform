export interface IPlayerActivityLogs {
  id?: string;
  playerId?: string | null;
  action?: string | null;
  beforeBalance?: number | null;
  afterBalance?: number | null;
}

export class PlayerActivityLogs implements IPlayerActivityLogs {
  constructor(
    public id?: string,
    public playerId?: string | null,
    public action?: string | null,
    public beforeBalance?: number | null,
    public afterBalance?: number | null,
  ) {}
}
