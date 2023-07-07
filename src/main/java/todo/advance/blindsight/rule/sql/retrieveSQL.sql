-- Retrieve profiles along with their associated account details
select a.account_id, a.email, p.profile_id, p.username, p.avatar, p.bio, p.date_join
from account a
join profile p on a.account_id = p.account_id

-- Retrieve all profiles with their respective sub-cards
select p.profile_id, p.username, p.avatar, sc.sub_card_id, sc.sub_card_name
from profile p
left join profile_sub_card psp on psp.profile_id = p.profile_id
left join sub_card sc on sc.sub_card_id = psp.sub_card_id;

-- Retrieve the number of todos for each profile
select p.profile_id, p.username, p.avatar, 
sc.sub_card_id, sc.sub_card_name, 
count(t.todo_id) as number_of_todos
from profile p
left join profile_sub_card psp on psp.profile_id = p.profile_id
left join sub_card sc on sc.sub_card_id = psp.sub_card_id
left join todo t on t.sub_card_id = sc.sub_card_id
group by p.profile_id, sc.sub_card_id
order by sc.sub_card_due_date;

-- Retrieve profiles with their associated roles
select a.account_id, p.profile_id, p.username, p.avatar, r.role_id, r.role_name
from account a
left join profile p on a.account_id = p.account_id
left join role r on a.account_id = r.account_id
order by r.role_name