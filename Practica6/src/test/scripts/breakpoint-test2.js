import http from 'k6/http';
import { check } from 'k6';

export let options = {
    thresholds: {
        http_req_failed: [{
            threshold: 'rate<=0.01',
            abortOnFail: true,
        }]},
    stages: [
        { duration: '1m', target: 5000 },
        { duration: '1m', target: 20000 },
        { duration: '2m', target: 50000 },
        { duration: '2m', target: 100000 },
        { duration: '2m', target: 150000 },
        { duration: '2m', target: 200000 },
    ],
};

export default function () {
    const res = http.get('http://localhost:8080/medico/1');
    check(res, { 'status is 200': (r) => r.status === 200 });
}