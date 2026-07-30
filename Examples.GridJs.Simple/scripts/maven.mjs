import { spawn } from 'node:child_process';
import { existsSync } from 'node:fs';
import process from 'node:process';

const windows = process.platform === 'win32';
const wrapper = windows ? 'mvnw.cmd' : 'sh';
const mavenArgs = ['-Dmaven.repo.local=.m2/repository', ...process.argv.slice(2)];
const wrapperArgs = windows ? mavenArgs : ['./mvnw', ...mavenArgs];

if (!existsSync(windows ? 'mvnw.cmd' : 'mvnw')) {
  throw new Error('Maven wrapper was not found in Examples.GridJs.Simple.');
}

const child = spawn(wrapper, wrapperArgs, {
  stdio: 'inherit',
  shell: windows,
  env: {
    ...process.env,
    MAVEN_USER_HOME: '.m2',
  },
});

child.on('error', (error) => {
  console.error(`Unable to start the Maven wrapper: ${error.message}`);
  process.exit(1);
});

for (const signal of ['SIGINT', 'SIGTERM']) {
  process.on(signal, () => child.kill(signal));
}

child.on('exit', (code, signal) => {
  if (signal) process.kill(process.pid, signal);
  process.exit(code ?? 1);
});
