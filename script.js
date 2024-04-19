import http from 'k6/http';
import { sleep } from 'k6';

export const options = {
  vus: 100,
  duration: '15s',
};

export default function() {
  http.get('http://timeline-service.sns.svc.cluster.local:8080/api/timeline');
}
