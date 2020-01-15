import { Moment } from 'moment';

export interface IMessage {
  id?: number;
  subject?: string;
  body?: string;
  dateCreated?: Moment;
  dateUpdated?: Moment;
}

export const defaultValue: Readonly<IMessage> = {};
