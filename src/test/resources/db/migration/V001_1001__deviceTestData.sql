INSERT INTO t_device(id, operating_system, user_id, device_id, created_on, updated_on)
VALUES ('b0622ba5-2b4d-4463-a2e8-595c89bdfad4', 'IOS', '42', 'PRESENT', now(), NULL),
       ('8de181f3-75f0-4320-9f9f-8cbb96c113cc', 'IOS', '42', 'TO_BE_UPDATED', now(), NULL),
       ('860673e4-f1c2-467d-b565-1f3c9a3169e5', 'ANDROID', '84', 'LIST_DEVICE', now() - INTERVAL '5 HOUR', now() - INTERVAL '1 HOUR'),
       ('9d4a2eb2-3477-459e-8563-91d33b00b22e', 'IOS', '84', 'LIST_DEVICE2', now(), NULL);
