import { type IPlayerActivityLogs } from '@/shared/model/player-activity-logs.model';

export interface IPlayerinfo {
  id?: string;
  playerId?: string | null;
  playerName?: string | null;
  balance?: number | null;
  playerActivityLogs?: IPlayerActivityLogs | null;
}

export class Playerinfo implements IPlayerinfo {
  constructor(
    public id?: string,
    public playerId?: string | null,
    public playerName?: string | null,
    public balance?: number | null,
    public playerActivityLogs?: IPlayerActivityLogs | null,
  ) {}
}
