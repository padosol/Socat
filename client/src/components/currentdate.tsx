"use client"

import dayjs from 'dayjs';

export default function CurrentDate({date}: {date: string}) {
  const now = dayjs(date).format('YYYY-MM-DD');

  return <span>{now}</span>
}