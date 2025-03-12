import { Category } from "./Category.mode";

export interface CategoryResponse{
    status:boolean;
    message:string;
    data:Category[];
}