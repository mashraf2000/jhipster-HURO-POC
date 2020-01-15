import { Moment } from 'moment';

export interface IGroupMessage {
  id?: number;
  dateCreated?: Moment;
  dateUpdated?: Moment;
}

export const defaultValue: Readonly<IGroupMessage> = {};
