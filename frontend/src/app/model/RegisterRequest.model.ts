import { GENDER } from "../enums/gender.enum";

export interface RegisterRequest {
    email: string;
    password: string;
    dob: Date;
    gender: GENDER;
  }